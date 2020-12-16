import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { HttpResponse } from '@angular/common/http';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { Subscription } from 'rxjs';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { MatDialog } from '@angular/material/dialog';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RecipeDeleteDialogComponent } from './recipe-delete-dialog.component';

@Component({
  selector: 'jhi-recipe-user-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.scss'],
})
export class RecipeListComponent implements OnInit {
  recipes: IRecipe[] = [];
  displayRecipes: IRecipe[] = [];
  account: Account | null = null;
  authSubscription?: Subscription;
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
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));

    this.getRecipes();

    this.recipeService.refreshNeeded$.subscribe(() => {
      this.getRecipes();
    });
  }

  getRecipes(): any {
    this.recipeService.query().subscribe(
      (res: HttpResponse<IRecipe[]>) => {
        if (res.body !== null) {
          res.body.reverse(),
            (this.displayRecipes = []),
            res.body.forEach(recipe => {
              if (recipe.userId === this.account?.id && recipe.status === this.statusOptions[0]) {
                this.displayRecipes.push(recipe);
              }
            });
        }
      },
      () => this.onError()
    );
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

  delete(recipe: IRecipe): void {
    const modalRef = this.modalService.open(RecipeDeleteDialogComponent, {
      size: 'md',
      backdrop: 'static',
      centered: true,
    });
    modalRef.componentInstance.recipe = recipe;
  }
}
