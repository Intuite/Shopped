import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { IRecipe, Recipe } from 'app/shared/model/recipe.model';
import { Status } from 'app/shared/model/enumerations/status.model';

describe('Service Tests', () => {
  describe('Recipe Service', () => {
    let injector: TestBed;
    let service: RecipeService;
    let httpMock: HttpTestingController;
    let elemDefault: IRecipe;
    let expectedResult: IRecipe | IRecipe[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RecipeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Recipe(0, 'AAAAAAA', 0, 'AAAAAAA', 0, currentDate, 'image/png', 'AAAAAAA', Status.ACTIVE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            creation: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Recipe', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            creation: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creation: currentDate,
          },
          returnedFromService
        );

        service.create(new Recipe()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Recipe', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            portion: 1,
            description: 'BBBBBB',
            duration: 1,
            creation: currentDate.format(DATE_TIME_FORMAT),
            image: 'BBBBBB',
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creation: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Recipe', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            portion: 1,
            description: 'BBBBBB',
            duration: 1,
            creation: currentDate.format(DATE_TIME_FORMAT),
            image: 'BBBBBB',
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            creation: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Recipe', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
