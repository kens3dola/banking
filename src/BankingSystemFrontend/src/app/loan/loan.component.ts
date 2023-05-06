import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ServiceService } from "app/services/service.service";
import { ToastrService } from "ngx-toastr";
import { formatDate } from "@angular/common";

@Component({
    selector: "app-loan",
    templateUrl: "./loan.component.html",
    styleUrls: ["./loan.component.css"],
})
export class LoanComponent implements OnInit {
    loanForm: FormGroup;
    accountNo: string;
    balance: number;
    loans: any;

    constructor(private service: ServiceService, private toastr: ToastrService) {}

    ngOnInit(): void {
        this.fetchAccountDetails();
        this.getLoanHistory();
        this.loanForm = new FormGroup({
            amount: new FormControl(null, Validators.required),
            duration: new FormControl(null, Validators.required),
        });
        this.loanForm.controls["duration"].setValue(1, { onlySelf: true });
    }

    loanFormSubmit() {
        console.log(this.loanForm.value);
        const savingDetails = {
            amount: this.loanForm.value.amount,
            duration: this.loanForm.value.duration,
        };
        this.service.loan(savingDetails).subscribe((response) => {
            this.toastr.success(response["message"]);
            setTimeout(() => location.reload(), 1000);
        });
    }

    private fetchAccountDetails() {
        this.service.onGetAccount().subscribe((response) => {
            this.accountNo = response["accountNo"];
            this.balance = response["balance"];
        });
    }

    getLoanHistory() {
        this.service.getLoanHistory().subscribe((response) => {
            this.loans = response;
            console.log(this.loans);
        });
    }

    formatDate(date) {
        return formatDate(date, "mediumDate", "en-us", "+530");
    }
}
