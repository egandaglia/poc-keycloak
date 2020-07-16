import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AppGuard } from './app.guard';
import { AppKeycloakGuard } from './app-keycloak.guard';


const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    canActivate: [AppKeycloakGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
