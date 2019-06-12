import {HttpClient} from '@angular/common/http';
import {Component, ViewChild, AfterViewInit} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {merge, Observable, of as observableOf} from 'rxjs';
import {catchError, map, startWith, switchMap} from 'rxjs/operators';
import { TransactionService } from 'src/app/services/transaction.service';
import { Transaction } from 'src/app/model/transaction';

/**
 * @title Table retrieving data through HTTP
 */
@Component({
  selector: 'app-transactions-component',
  styleUrls: ['transactions.component.scss'],
  templateUrl: 'transactions.component.html',
})
export class TransactionsComponent implements AfterViewInit {
  displayedColumns: string[] = ['amount','date', 'title'];
  data: Transaction[] = [];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private transactionService: TransactionService) {}

  ngAfterViewInit() {

    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.transactionService!.getPaged(
            this.paginator.pageSize, this.paginator.pageIndex);
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          console.log(data.totalEntries);
          
          this.resultsLength = data.totalEntries;

          return data.transactions;
        }),
        catchError(err => {
          console.log(err);
          
          this.isLoadingResults = false;
          // Catch if the GitHub API has reached its rate limit. Return empty data.
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      ).subscribe(data => this.data = data);
  }
}

/** An example database that the data source uses to retrieve data for the table. */
