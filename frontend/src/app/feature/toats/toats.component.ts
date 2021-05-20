import { Component, OnInit } from "@angular/core";
import { ToastService } from "../../core/services/toast.service";

@Component({
  selector: "app-toasts",
  templateUrl: "./toats.component.html",
  styleUrls: ["./toats.component.css"],
  host: { "[class.ngb-toasts]": "true" },
})
export class ToatsComponent implements OnInit {
  constructor(public toastService: ToastService) {}

  ngOnInit(): void {}
}
