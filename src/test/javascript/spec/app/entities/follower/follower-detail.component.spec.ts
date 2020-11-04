import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { FollowerDetailComponent } from 'app/entities/follower/follower-detail.component';
import { Follower } from 'app/shared/model/follower.model';

describe('Component Tests', () => {
  describe('Follower Management Detail Component', () => {
    let comp: FollowerDetailComponent;
    let fixture: ComponentFixture<FollowerDetailComponent>;
    const route = ({ data: of({ follower: new Follower(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [FollowerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FollowerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FollowerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load follower on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.follower).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
