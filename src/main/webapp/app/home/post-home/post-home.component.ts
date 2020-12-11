import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { HttpResponse } from '@angular/common/http';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { MatDialog } from '@angular/material/dialog';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';
import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

interface CardPost {
  id?: number;
  caption?: string;
  date?: Moment;
  status?: Status;
  recipeName?: string;
  recipeId?: number;
  userLogin?: string;
  userId?: number;
  imageContentType?: string;
  image?: any;
  portion?: number;
  duration?: number;
}

@Component({
  selector: 'jhi-post-home',
  templateUrl: './post-home.component.html',
  styleUrls: ['./post-home.component.scss'],
})
export class PostHomeComponent implements OnInit {
  // recipes: IRecipe[] = [];
  recipe: any;
  posts: IPost[] = [];
  cardPosts: CardPost[] = [];
  account?: Account;
  user?: IUser;
  statusOptions = ['ACTIVE', 'INACTIVE', 'BLOCKED'];
  searchText = '';

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    protected recipeService: RecipeService,
    protected postService: PostService,
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

    this.getPosts();

    this.postService.refreshNeeded$.subscribe(() => {
      this.getPosts();
    });
  }

  getPosts(): any {
    this.postService.query().subscribe(
      (res: HttpResponse<IRecipe[]>) => (this.onSuccessPost(res.body), this.cardPosts.reverse()),
      () => this.onError()
    );
  }

  cleanPosts(): void {
    let i = 0;
    while (i < this.posts.length) {
      if (this.posts[i].status === this.statusOptions[1] || this.posts[i].status === this.statusOptions[2]) {
        this.posts.splice(i, 1);
      } else {
        i++;
      }
    }
    this.joinRecipeToCard();
  }

  joinRecipeToCard(): void {
    this.posts.forEach(post =>
      this.recipeService.find(post.recipeId).subscribe(recipe => {
        if (recipe.body !== null) {
          this.cardPosts.push({
            id: post.id,
            caption: post.caption,
            date: post.date,
            status: post.status,
            recipeName: post.recipeName,
            recipeId: post.recipeId,
            userLogin: post.userLogin,
            userId: post.userId,
            imageContentType: recipe.body.imageContentType,
            image: recipe.body.image,
            portion: recipe.body.portion,
            duration: recipe.body.duration,
          });
        }
      })
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
    console.warn('There was an error');
  }

  private onSuccessPost(body: IPost[] | null): void {
    this.posts = body || [];
    this.cleanPosts();
  }
}
