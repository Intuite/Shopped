import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { LogTypeDetailComponent } from 'app/entities/log-type/log-type-detail.component';
import { LogType } from 'app/shared/model/log-type.model';

describe('Component Tests', () => {
  describe('LogType Management Detail Component', () => {
    let comp: LogTypeDetailComponent;
    let fixture: ComponentFixture<LogTypeDetailComponent>;
    const route = ({ data: of({ logType: new LogType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [LogTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LogTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LogTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load logType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.logType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
