import { AuthService } from './../../../shared/services/auth/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthQuery } from 'src/app/shared';
import { PreviousRouteService } from 'src/app/router/previous.route.service';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

	public username = new FormControl('', [
		Validators.required
	]);
	public password = new FormControl('', [
		Validators.required
	]);
	returnUrl: string;

	constructor(
		private router: Router,
		private route: ActivatedRoute,
		private authQuery: AuthQuery,
		private authService: AuthService,
		private previousRouteService: PreviousRouteService
	) { }

	ngOnInit() {
		this.loadTitle();
		this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
	}
	title: string = '';

	loadTitle = () => {
		this.authQuery.loginTitle$.subscribe((title) => {
			this.title = title;
		})
	}

	onSubmit(): void {

		if (!this.username.valid || !this.password.valid) {
			return;
		}

		this.authService.login({
			username: this.username.value,
			password: this.password.value
		}).subscribe(() => {
			this.router.navigateByUrl(this.returnUrl);
		});

	}

	getErrorMessage(field) {
		if (this[field].hasError('required')) {
			return `VALIDATION.${field.toUpperCase()}_REQUIRED`;
		}
		return '';
	}

}
