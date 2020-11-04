import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { LogTypeUpdateComponent } from 'app/entities/log-type/log-type-update.component';
import { LogTypeService } from 'app/entities/log-type/log-type.service';
import { LogType } from 'app/shared/model/log-type.model';

describe('Component Tests', () => {
  describe('LogType Management Update Component', () => {
    let comp: LogTypeUpdateComponent;
    let fixture: ComponentFixture<LogTypeUpdateComponent>;
    let service: LogTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [LogTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LogTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LogTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LogTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LogType(123);
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
        const entity = new LogType();
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
