import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { HttpResponse } from '@angular/common/http';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { Status } from 'app/shared/model/enumerations/status.model';
import { MatDialog } from '@angular/material/dialog';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-post-user-list',
  templateUrl: './post-user-list.component.html',
  styleUrls: ['./post-user-list.component.scss'],
})
export class PostUserListComponent implements OnInit, AfterViewInit {
  recipes: IRecipe[] = [];
  account?: Account;
  user!: IUser;
  statusOptions = ['ACTIVE', 'INACTIVE'];
  searchText = '';

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    protected recipeService: RecipeService,
    protected userService: UserService,
    private accountService: AccountService,
    public dialog: MatDialog,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      if (account) {
        this.userService.find(account.login).subscribe(user => (this.user = user));
      }
    });

    this.recipeService.query().subscribe(
      (res: HttpResponse<IRecipe[]>) => this.onSuccess(res.body),
      () => this.onError()
    );

    this.recipeService.refreshNeeded$.subscribe(() => {
      this.getRecipes();
    });
  }

  getRecipes(): any {
    this.recipeService.query().subscribe(
      (res: HttpResponse<IRecipe[]>) => this.onSuccess(res.body),
      () => this.onError()
    );
  }

  ngAfterViewInit(): void {}

  cleanRecipes(): void {
    let i = 0;
    while (i < this.recipes.length) {
      if (this.recipes[i].userId !== this.user.id || this.recipes[i].status === this.statusOptions[1]) {
        this.recipes.splice(i, 1);
      } else {
        i++;
      }
    }
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }

  private onError(): void {
    console.warn('There are no Recipes');
  }

  private onSuccess(body: IRecipe[] | null): void {
    this.recipes = body || [];
    this.cleanRecipes();
  }

  setStatus(element: IRecipe, newStatus: boolean): void {
    this.recipeService
      .update({
        ...element,
        status: !newStatus ? (Status.ACTIVE.toUpperCase() as Status) : (Status.INACTIVE.toUpperCase() as Status),
      })
      .subscribe(() => {
        // this.loadPage(this.page);
      });
  }
}
