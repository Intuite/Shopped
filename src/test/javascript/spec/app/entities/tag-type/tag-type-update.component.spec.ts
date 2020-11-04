import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { TagTypeUpdateComponent } from 'app/entities/tag-type/tag-type-update.component';
import { TagTypeService } from 'app/entities/tag-type/tag-type.service';
import { TagType } from 'app/shared/model/tag-type.model';

describe('Component Tests', () => {
  describe('TagType Management Update Component', () => {
    let comp: TagTypeUpdateComponent;
    let fixture: ComponentFixture<TagTypeUpdateComponent>;
    let service: TagTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [TagTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TagTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TagTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TagTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TagType(123);
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
        const entity = new TagType();
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
