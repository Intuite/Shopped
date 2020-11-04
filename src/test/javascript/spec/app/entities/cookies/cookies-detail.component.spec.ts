import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { CookiesDetailComponent } from 'app/entities/cookies/cookies-detail.component';
import { Cookies } from 'app/shared/model/cookies.model';

describe('Component Tests', () => {
  describe('Cookies Management Detail Component', () => {
    let comp: CookiesDetailComponent;
    let fixture: ComponentFixture<CookiesDetailComponent>;
    const route = ({ data: of({ cookies: new Cookies(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [CookiesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CookiesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CookiesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cookies on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cookies).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
