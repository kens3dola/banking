import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ServiceService } from "app/services/service.service";
import { ToastrService } from "ngx-toastr";
import { formatDate } from "@angular/common";

@Component({
    selector: "app-saving",
    templateUrl: "./saving.component.html",
    styleUrls: ["./saving.component.css"],
})
export class SavingComponent implements OnInit {
    savingMoneyForm: FormGroup;
    accountNo: string;
    balance: number;
    savings: any;

    constructor(private service: ServiceService, private toastr: ToastrService) {}

    ngOnInit(): void {
        this.fetchAccountDetails();
        this.getSavingHistory();
        this.savingMoneyForm = new FormGroup({
            duration: new FormControl(null, Validators.required),
            amount: new FormControl(null, Validators.required),
        });
        this.savingMoneyForm.controls["duration"].setValue(1, { onlySelf: true });
    }

    savingMoneyFormSubmit() {
        const savingDetails = {
            amount: this.savingMoneyForm.value.amount,
            duration: this.savingMoneyForm.value.duration,
        };
        this.service.saving(savingDetails).subscribe((response) => {
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

    getSavingHistory() {
        this.service.getSavingHistory().subscribe((response) => {
            this.savings = response;
            console.log(this.savings);
        });
    }

    formatDate(date) {
        return formatDate(date, "mediumDate", "en-us", "+530");
    }
}
