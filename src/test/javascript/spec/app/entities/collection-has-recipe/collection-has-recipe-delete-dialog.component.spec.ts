import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ShoppedTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { CollectionHasRecipeDeleteDialogComponent } from 'app/entities/collection-has-recipe/collection-has-recipe-delete-dialog.component';
import { CollectionHasRecipeService } from 'app/entities/collection-has-recipe/collection-has-recipe.service';

describe('Component Tests', () => {
  describe('CollectionHasRecipe Management Delete Component', () => {
    let comp: CollectionHasRecipeDeleteDialogComponent;
    let fixture: ComponentFixture<CollectionHasRecipeDeleteDialogComponent>;
    let service: CollectionHasRecipeService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [CollectionHasRecipeDeleteDialogComponent],
      })
        .overrideTemplate(CollectionHasRecipeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CollectionHasRecipeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollectionHasRecipeService);
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
