import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ShoppedTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { RecipeHasRecipeTagDeleteDialogComponent } from 'app/entities/recipe-has-recipe-tag/recipe-has-recipe-tag-delete-dialog.component';
import { RecipeHasRecipeTagService } from 'app/entities/recipe-has-recipe-tag/recipe-has-recipe-tag.service';

describe('Component Tests', () => {
  describe('RecipeHasRecipeTag Management Delete Component', () => {
    let comp: RecipeHasRecipeTagDeleteDialogComponent;
    let fixture: ComponentFixture<RecipeHasRecipeTagDeleteDialogComponent>;
    let service: RecipeHasRecipeTagService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [RecipeHasRecipeTagDeleteDialogComponent],
      })
        .overrideTemplate(RecipeHasRecipeTagDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecipeHasRecipeTagDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecipeHasRecipeTagService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
