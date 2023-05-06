import { Component, OnInit } from "@angular/core";
import { ServiceService } from "../services/service.service";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";

@Component({
    selector: "app-account",
    templateUrl: "./account.component.html",
    styleUrls: ["./account.component.css"],
})
export class AccountComponent implements OnInit {
    accountNo: string;
    balance: number;
    withdrawDepositFrom: FormGroup;

    constructor(private service: ServiceService, private toastr: ToastrService) {}

    ngOnInit(): void {
        this.fetchAccountDetails();
        this.withdrawDepositFrom = new FormGroup({
            amount: new FormControl(null, Validators.required),
            type: new FormControl(null, Validators.required),
        });
        this.withdrawDepositFrom.controls["type"].setValue("withdraw", { onlySelf: true });
    }

    withdrawDepositFromSubmit() {
        console.log(this.withdrawDepositFrom.value);
        const withdrawDepositDetails = {
            amount: this.withdrawDepositFrom.value.amount,
            type: this.withdrawDepositFrom.value.type,
        };
        this.service.withdrawDeposit(withdrawDepositDetails).subscribe((response) => {
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
}
