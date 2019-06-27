import { Injectable } from "@angular/core";
import { Tag } from "../model/hashtag-system/tag";
import { HttpClient } from "@angular/common/http";
import { StatusServiceService } from "./status-service.service";
import { UserService } from "./user.service";
import { Subscription } from "rxjs";

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
  apiURL: string = "/api/tag";
  subscription: Subscription;
  allTags: Tag[] = [];


  constructor(
    private httpClient: HttpClient,
    private statusService: StatusServiceService
  ) {
    this.subscription = this.statusService.getMessage().subscribe(status => {
      if (status.loggedIn == false) {
        this.allTags = [];
      }
      if (status.loggedIn == true) {
        this.getAllTags();
      }
    });
    this.getAllTags();
  }

  getColor() {
    return this.colors[Math.floor(Math.random() * this.colors.length)];
  }
  getColorPreset() {
    return this.colors;
  }

  public getAllTags() {
    if (this.allTags.length === 0) {
      this.httpClient.get<Tag[]>(`${this.apiURL}/all`).subscribe(tags => {
        this.allTags = tags;
        return this.allTags;
      });
    } else {
      return this.allTags;
    }
  }

  public refreshAllTags(){
    this.httpClient.get<Tag[]>(`${this.apiURL}/all`).subscribe(tags => {
      this.allTags = tags;
    });
  }

  public getTag(tagid: string): Tag {
    return this.allTags.find(
      t =>
        t.title.toLocaleLowerCase().trim() === tagid.toLocaleLowerCase().trim()
    )
      ? this.allTags.find(
          t =>
            t.title.toLocaleLowerCase().trim() ===
            tagid.toLocaleLowerCase().trim()
        )
      : {
          title: tagid,
          color: this.getColor(),
          user: undefined,
          transactions:[],
          id: undefined
        };
  }

  public addTags(tags: Tag[]) {
    tags.forEach(tag => {
      if (!this.allTags.includes(tag)) {
        this.allTags.push(tag);
      }
    });
    this.statusService.sendMessage({ saved: false });
  }
  public saveAndUpdateTagList() {
    console.log(this.allTags);
    return this.httpClient
      .post<Tag[]>(`${this.apiURL}/all`, this.allTags)
      .subscribe(data => {
        
        
        this.statusService.sendMessage({ saved: true });
        this.allTags = data;
      });
  }
}
