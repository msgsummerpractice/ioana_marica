import { Component } from '@angular/core';
import { MaskPipe } from './mask.pipe';

@Component({
  selector: 'app-login',
  imports: [MaskPipe],
  templateUrl: './login.html',
})
export class Login {
  username = 'johndoe';
  email = 'john@example.com';
}
