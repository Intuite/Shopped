import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { PostService } from 'app/entities/post/post.service';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { IPost } from 'app/shared/model/post.model';
import { User } from 'app/core/user/user.model';
import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

interface RecipePost {
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
  selector: 'jhi-general-data',
  templateUrl: './general-data.component.html',
  styleUrls: ['./general-data.component.scss', './../../../../home/post-home/post-home.component.scss'],
})
export class GeneralDataComponent implements OnInit {
  @Input() user!: User | null;
  totalItems = 0;
  posts: IPost[] = [];
  recipePosts: RecipePost[] = [];
  statusOptions = ['ACTIVE', 'INACTIVE', 'BLOCKED'];
  requesting = false;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected postService: PostService,
    protected recipeService: RecipeService
  ) {}

  ngOnInit(): void {
    if (this.user) this.getUserPosts();
  }

  getUserPosts(): void {
    this.requesting = true;
    this.postService
      .queryAll({
        ...{ 'userId.equals': this.user?.id },
      })
      .subscribe(
        (res: HttpResponse<IPost[]>) => this.onSuccess(res.body, res.headers),
        () => this.onError()
      );
  }

  private onError(): void {
    console.warn('There was an error');
  }

  private onSuccess(data: IPost[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    if (data) this.posts = data;
    this.requesting = false;
    this.joinRecipe();
  }

  joinRecipe(): void {
    this.posts.forEach(post => {
      if (post !== null) {
        this.recipeService.find(post.recipeId).subscribe(recipe => {
          if (recipe.body !== null) {
            this.recipePosts.push({
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
}
