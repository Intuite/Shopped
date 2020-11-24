import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';
import { Subscription } from 'rxjs';

import { IRecipe } from 'app/shared/model/recipe.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.scss'],
})
export class RecipeDetailComponent implements OnInit {
  recipe: IRecipe | null = null;
  eventSubscriber?: Subscription;
  statusOptions = ['ACTIVE', 'INACTIVE'];
  user!: IUser;

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    protected userService: UserService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recipe }) => (this.recipe = recipe));

    this.accountService.getAuthenticationState().subscribe(account => {
      if (account) {
        this.userService.find(account.login).subscribe(user => (this.user = user));
      }
    });
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
}
