import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { BiteDetailComponent } from 'app/entities/bite/bite-detail.component';
import { Bite } from 'app/shared/model/bite.model';

describe('Component Tests', () => {
  describe('Bite Management Detail Component', () => {
    let comp: BiteDetailComponent;
    let fixture: ComponentFixture<BiteDetailComponent>;
    const route = ({ data: of({ bite: new Bite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [BiteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BiteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BiteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
