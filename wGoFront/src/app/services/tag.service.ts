import { Injectable } from "@angular/core";
import { Tag } from "../model/tag";
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: "root"
})
export class TagService {
  light_colors = [
    "#ffcdd2",
    "#F8BBD0",
    "#E1BEE7",
    "#D1C4E9",
    "#C5CAE9",
    "#BBDEFB",
    "#B3E5FC",
    "#B2EBF2",
    "#B2DFDB",
    "#C8E6C9",
    "#DCEDC8",
    "#F0F4C3",
    "#FFF9C4",
    "#FFECB3",
    "#FFE0B2",
    "#FFCCBC",
    "#D7CCC8",
    "#F5F5F5",
    "#CFD8DC"
  ];
  colors = [
    "#f44336",
    "#E91E63",
    "#9C27B0",
    "#673AB7",
    "#3F51B5",
    "#2196F3",
    "#03A9F4",
    "#00BCD4",
    "#009688",
    "#4CAF50",
    "#8BC34A",
    "#CDDC39",
    "#FFEB3B",
    "#FFC107",
    "#FF9800",
    "#FF5722",
    "#795548",
    "#9E9E9E",
    "#607D8B"
  ];
  apiURL: string = "http://localhost:8080/tag";
  constructor(private httpClient: HttpClient) {}

  getColor() {
    return this.colors[Math.floor(Math.random() * this.colors.length)];
  }

  public getAllTags() {
    return this.httpClient.get<Tag[]>(`${this.apiURL}/all`);
  }
}
