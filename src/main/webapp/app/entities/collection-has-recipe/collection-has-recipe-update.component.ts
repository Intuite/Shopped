import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICollectionHasRecipe, CollectionHasRecipe } from 'app/shared/model/collection-has-recipe.model';
import { CollectionHasRecipeService } from './collection-has-recipe.service';
import { ICollection } from 'app/shared/model/collection.model';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Status } from 'app/shared/model/enumerations/status.model';
import { CollectionHasRecipeComponent } from 'app/entities/collection-has-recipe/collection-has-recipe.component';

type SelectableEntity = ICollection | IRecipe;

@Component({
  selector: 'jhi-collection-has-recipe-update',
  templateUrl: './collection-has-recipe-update.component.html',
})
export class CollectionHasRecipeUpdateComponent implements OnInit {
  isSaving = false;
  recipes: IRecipe[] = [];
  // collections: ICollection[] = [];
  currentCollection?: ICollection;
  currentRecipe?: IRecipe;

  constructor(
    protected collectionHasRecipeService: CollectionHasRecipeService,
    // protected collectionService: CollectionService,
    protected recipeService: RecipeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    // this.collectionService.query().subscribe((res: HttpResponse<ICollection[]>) => (this.collections = res.body || []));
  }

  // previousState(): void {
  //   window.history.back();
  // }

  close(): void {
    this.activeModal.close();
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  save(): void {
    this.isSaving = true;
    const collectionHasRecipe = this.buildEntry();
    if (collectionHasRecipe.id !== undefined) {
      this.subscribeToSaveResponse(this.collectionHasRecipeService.update(collectionHasRecipe));
    } else {
      this.subscribeToSaveResponse(this.collectionHasRecipeService.create(collectionHasRecipe));
    }
  }

  viewCollectionContent(collection: ICollection): void {
    const modalRef = this.modalService.open(CollectionHasRecipeComponent, { size: 'xl', backdrop: 'static', centered: true });
    modalRef.componentInstance.collection = collection;
  }

  private buildEntry(): ICollectionHasRecipe {
    return {
      ...new CollectionHasRecipe(),
      status: 'ACTIVE' as Status,
      collectionId: this.currentCollection?.id,
      recipeId: this.currentRecipe?.id,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollectionHasRecipe>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.eventManager.broadcast('collectionHasRecipeListModification');
    // this.close();
    if (this.currentCollection) this.viewCollectionContent(this.currentCollection);
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
