import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { HttpResponse } from '@angular/common/http';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { Subscription } from 'rxjs';
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
  recipe: any;
  posts: IPost[] = [];
  cardPosts: CardPost[] = [];
  displayCardPosts: CardPost[] = [];
  account: Account | null = null;
  authSubscription?: Subscription;
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
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));

    this.getPosts();

    this.postService.refreshNeeded$.subscribe(() => {
      this.displayCardPosts = [];
      this.getPosts();
    });
  }

  getPosts(): any {
    this.displayCardPosts = [];
    this.postService.query().subscribe(
      (res: HttpResponse<any[]>) => {
        if (res.body !== null) {
          res.body.reverse(),
            res.body.forEach(post => {
              if (post.status === this.statusOptions[0]) {
                this.recipeService.find(post.recipeId).subscribe(recipe => {
                  if (recipe.body !== null) {
                    this.displayCardPosts.push({
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
                });
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
    console.warn('There was an error');
  }
}
