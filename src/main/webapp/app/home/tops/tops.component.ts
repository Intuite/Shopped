import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { IBite } from 'app/shared/model/bite.model';
import { BiteService } from 'app/entities/bite/bite.service';
import { Status } from 'app/shared/model/enumerations/status.model';
import { PostService } from 'app/entities/post/post.service';
import { RecipeService } from 'app/entities/recipe/recipe.service';

interface TopItem {
  id?: number;
  status?: Status;
  recipeName?: string;
  recipeId?: number;
  userLogin?: string;
  userId?: number;
}

@Component({
  selector: 'jhi-tops',
  templateUrl: './tops.component.html',
  styleUrls: ['./tops.component.scss'],
})
export class TopsComponent implements OnInit {
  bites: IBite[] = [];
  topItems: TopItem[] = [];
  statusOptions = ['ACTIVE', 'INACTIVE', 'BLOCKED'];

  constructor(
    protected biteService: BiteService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected postService: PostService,
    protected recipeService: RecipeService
  ) {}

  ngOnInit(): void {
    this.biteService.query().subscribe(
      (res: HttpResponse<IBite[]>) => this.onSuccessPost(res.body),
      () => this.onError()
    );
  }

  private onError(): void {
    console.warn('There was an error');
  }

  private onSuccessPost(body: IBite[] | null): void {
    this.bites = body || [];
    // this.countBites(body);
    this.joinPost();
  }

  countBites(bites: IBite[] | null): void {
    if (bites !== null) {
      for (let i = 0; bites.length; i++) {
        // const num: any = bites[i].postId;
        // count[num] = count[num] ? count[num] +1 :1;
      }
    }
  }

  joinPost(): void {
    if (this.bites !== null) {
      this.bites.forEach(bite =>
        this.postService.find(bite.postId).subscribe(post => {
          if (post.body !== null) {
            this.topItems.push({
              id: post.body.id,
              recipeName: post.body.recipeName,
              recipeId: post.body.recipeId,
              userLogin: post.body.userLogin,
              userId: post.body.userId,
            });
          }
        })
      );
    }
  }
}
