import { BrowserModule } from '@angular/platform-browser';
import { NgModule, DoBootstrap, ApplicationRef } from '@angular/core';
import { FormsModule }   from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';
import { HomeComponent } from './home/home.component';
import { ArticleModule } from './article/article.module';
import { AuthModule } from './auth/auth.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BasicAuthInterceptor } from './core/interceptors/basic-auth.interceptor';
import { ErrorInterceptor } from './core/interceptors/error.interceptor';

import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { environment } from 'src/environments/environment';

const keycloakService = new KeycloakService();
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
  ],
  imports: [
    KeycloakAngularModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ArticleModule,
    AuthModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    {
      provide: KeycloakService,
      useValue: keycloakService,
    }
  ],
  entryComponents: [AppComponent]
})
export class AppModule implements DoBootstrap {

  ngDoBootstrap(appRef: ApplicationRef): void {
    keycloakService
    .init({config: environment.keycloakConfig})
    .then(() => {
      console.log("doBoostrap: keycloakService initialized");
      appRef.bootstrap(AppComponent);
    }).catch((error) =>
      console.error("doBoostrap init keycloak failed", error)
    );
  }
}
