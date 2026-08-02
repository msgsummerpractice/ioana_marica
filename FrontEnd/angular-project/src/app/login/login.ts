import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { MaskPipe } from './mask.pipe';
import { ReactiveFormsModule, NonNullableFormBuilder, Validators } from '@angular/forms';
import { LoginForm } from '../models/loginForm.model';
import { Authentication } from './authService';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, MaskPipe],
  templateUrl: './login.html',
})
export class Login {
  private readonly fb = inject(NonNullableFormBuilder);
  private readonly auth = inject(Authentication);
  private readonly router = inject(Router);

  protected readonly loginForm = this.fb.group<LoginForm>({
    email: this.fb.control('', [Validators.required, Validators.email]),
    password: this.fb.control('', [Validators.required, Validators.minLength(6)]),
  });

  onSubmit(): void {
    if (this.loginForm.valid) {
      console.log(this.loginForm.getRawValue());

      this.auth.login();
      this.router.navigate(['/']);
    }
  }
}
