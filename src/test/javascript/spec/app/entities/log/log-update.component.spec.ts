import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { LogUpdateComponent } from 'app/entities/log/log-update.component';
import { LogService } from 'app/entities/log/log.service';
import { Log } from 'app/shared/model/log.model';

describe('Component Tests', () => {
  describe('Log Management Update Component', () => {
    let comp: LogUpdateComponent;
    let fixture: ComponentFixture<LogUpdateComponent>;
    let service: LogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [LogUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LogUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LogService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Log(123);
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
        const entity = new Log();
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
