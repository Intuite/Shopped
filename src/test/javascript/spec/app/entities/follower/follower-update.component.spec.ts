import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { FollowerUpdateComponent } from 'app/entities/follower/follower-update.component';
import { FollowerService } from 'app/entities/follower/follower.service';
import { Follower } from 'app/shared/model/follower.model';

describe('Component Tests', () => {
  describe('Follower Management Update Component', () => {
    let comp: FollowerUpdateComponent;
    let fixture: ComponentFixture<FollowerUpdateComponent>;
    let service: FollowerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [FollowerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FollowerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FollowerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FollowerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Follower(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Follower();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
