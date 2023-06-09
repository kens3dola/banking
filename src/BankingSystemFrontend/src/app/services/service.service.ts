import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { catchError, tap } from "rxjs/operators";
import { throwError, BehaviorSubject, Subject } from "rxjs";
import { ToastrService } from "ngx-toastr";
import { environment } from "../../environments/environment";

@Injectable({
    providedIn: "root",
})
export class ServiceService {
    LoginUrl = environment.apiUrl + "/login";
    UserUrl = environment.apiUrl + "/user";

    private _refreshNeeded$ = new Subject<void>();
    private accountUrl: string = environment.apiUrl + "/account";
    private transferUrl: string = environment.apiUrl + "/transfer";
    private trasactionUrl: string = environment.apiUrl + "/transaction";
    private savingUrl: string = environment.apiUrl + "/saving";
    private savingHistoryUrl: string = environment.apiUrl + "/saving/history";
    private loanUrl: string = environment.apiUrl + "/loan";
    private loanHistoryUrl: string = environment.apiUrl + "/loan/history";
    private withdrawDepositUrl: string = environment.apiUrl + "/withdrawDeposit";

    constructor(private http: HttpClient, private toastr: ToastrService) {}

    get refreshNeeded$() {
        return this._refreshNeeded$;
    }

    loginCall(loginDetails) {
        return this.http.post(this.LoginUrl, loginDetails).pipe(catchError((error) => this.handleError(error)));
    }

    private handleError(error: HttpErrorResponse) {
        if (error && error["error"] && error["error"]["message"] === "Unauthorized") {
            this.toastr.error("Invalid username or password.");
            return throwError(error);
        }
        return throwError(this.toastr.error(error["error"]["message"]));
    }

    onFetchProfile() {
        let header = new HttpHeaders();
        header = header.append("token", sessionStorage.getItem("auth-token"));
        return this.http.get(this.UserUrl, { headers: header }).pipe(catchError((error) => this.handleError(error)));
    }

    onUpdateProfile(userDetails: { dob: string; name: string; email: string }) {
        let headers = new HttpHeaders();
        headers = headers.append("token", sessionStorage.getItem("auth-token"));
        const options = { headers };
        return this.http.put(this.UserUrl, userDetails, options).pipe(
            tap(() => {
                this.refreshNeeded$.next();
            }),
            catchError((error) => this.handleError(error))
        );
    }

    onRegister(userDetails) {
        return this.http.post(this.UserUrl, userDetails).pipe(catchError((error) => this.handleError(error)));
    }

    onGetAccount() {
        let headers = new HttpHeaders();
        headers = headers.append("token", sessionStorage.getItem("auth-token"));
        const options = { headers };
        return this.http.get(this.accountUrl, options).pipe(catchError((error) => this.handleError(error)));
    }

    transferMoney(transferDetails) {
        let headers = new HttpHeaders();
        headers = headers.append("token", sessionStorage.getItem("auth-token"));
        const options = { headers };
        return this.http.post(this.transferUrl, transferDetails, options).pipe(catchError((error) => this.handleError(error)));
    }

    getTransactions() {
        let headers = new HttpHeaders();
        headers = headers.append("token", sessionStorage.getItem("auth-token"));
        const options = { headers };
        return this.http.get(this.trasactionUrl, options).pipe(catchError((error) => this.handleError(error)));
    }

    saving(savingDetails) {
        let headers = new HttpHeaders();
        headers = headers.append("token", sessionStorage.getItem("auth-token"));
        const options = { headers };
        return this.http.post(this.savingUrl, savingDetails, options).pipe(catchError((error) => this.handleError(error)));
    }

    getSavingHistory() {
        let headers = new HttpHeaders();
        headers = headers.append("token", sessionStorage.getItem("auth-token"));
        const options = { headers };
        return this.http.get(this.savingHistoryUrl, options).pipe(catchError((error) => this.handleError(error)));
    }

    loan(loanDetails) {
        let headers = new HttpHeaders();
        headers = headers.append("token", sessionStorage.getItem("auth-token"));
        const options = { headers };
        return this.http.post(this.loanUrl, loanDetails, options).pipe(catchError((error) => this.handleError(error)));
    }

    getLoanHistory() {
        let headers = new HttpHeaders();
        headers = headers.append("token", sessionStorage.getItem("auth-token"));
        const options = { headers };
        console.log(this.withdrawDepositUrl, options);
        return this.http.get(this.loanHistoryUrl, options).pipe(catchError((error) => this.handleError(error)));
    }

    withdrawDeposit(withdrawDepositDetails) {
        let headers = new HttpHeaders();
        headers = headers.append("token", sessionStorage.getItem("auth-token"));
        const options = { headers };
        console.log(this.withdrawDepositUrl, options);
        return this.http
            .post(this.withdrawDepositUrl, withdrawDepositDetails, options)
            .pipe(catchError((error) => this.handleError(error)));
    }
}
