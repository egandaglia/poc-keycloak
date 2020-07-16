import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { User } from 'src/app/core/models/user.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  redirectUrl: string = '';

  constructor(private http: HttpClient, private router: Router, private keycloakService: KeycloakService) { 
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  signUp(serialCode: string, firstName: string, lastName: string, password: string) {
    let body = 
    {
      serialCode: serialCode,
      firstName: firstName,
      lastName: lastName,
      password: password
    }
    return this.http.post<User>(`${environment.baseUrl}/auth/register`, body)
    .pipe(map(user => {
      // store user basic auth credentials
      user.authData = window.btoa(serialCode + ':' + password);
      localStorage.setItem('currentUser', JSON.stringify(user));
      this.currentUserSubject.next(user);
      return user;
    }));
  }

  signIn(serialCode: string, password: string) {
    return this.http.post<User>(`${environment.baseUrl}/auth/login`, {serialCode, password})
    .pipe(map(user => {
      // store user basic auth credentials
      user.authData = window.btoa(serialCode + ':' + password);
      localStorage.setItem('currentUser', JSON.stringify(user));
      this.currentUserSubject.next(user);
      return user;
    }));
  }

  loadKeycloakUserProfile(): Promise<Keycloak.KeycloakProfile> {
    return this.keycloakService.loadUserProfile().then(
      profile => {
        let attributes = profile['attributes'];
        let user: User = {
          serialCode: attributes.serialCode,
          firstName: attributes.firstName,
          lastName: attributes.lastName,
          dtIns: attributes.dtIns,
          dtLastAccess: attributes.dtLastAccess
        }
        this.currentUserSubject.next(user);
        return profile;
      }
    )
  }

  
  logout() {
    // remove user from local storage
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    return this.router.navigateByUrl('/login');
  }

  logoutKeycloak() {
    this.currentUserSubject.next(null);
    this.keycloakService.logout();
    window.location.href = 'http://localhost:8080/auth/realms/keycloak-demo/protocol/openid-connect/logout?redirect_uri=encodedRedirectUri';
  }

  public get currentUserValue() : User {
    return this.currentUserSubject.value;
  }

  isLoggedIn(): boolean {
    return this.currentUserSubject.value != null;
  }


}
