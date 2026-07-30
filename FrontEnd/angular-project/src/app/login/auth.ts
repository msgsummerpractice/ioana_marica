import { Directive, effect, inject, TemplateRef, ViewContainerRef } from '@angular/core';
import { Authentification } from './authentification';

@Directive({
  selector: '[appAuth]',
})
export class Auth {
  private authService = inject(Authentification);
  private templateRef = inject(TemplateRef);
  private viewContainerRef = inject(ViewContainerRef);

  constructor() {
    effect(() => {
      const isAuth = this.authService.isAuthentificated();
      if (isAuth) {
        this.viewContainerRef.createEmbeddedView(this.templateRef);
      } else {
        this.viewContainerRef.clear();
      }
    });
  }
}
