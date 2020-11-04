import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { CommendationDetailComponent } from 'app/entities/commendation/commendation-detail.component';
import { Commendation } from 'app/shared/model/commendation.model';

describe('Component Tests', () => {
  describe('Commendation Management Detail Component', () => {
    let comp: CommendationDetailComponent;
    let fixture: ComponentFixture<CommendationDetailComponent>;
    const route = ({ data: of({ commendation: new Commendation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [CommendationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CommendationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommendationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commendation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commendation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
