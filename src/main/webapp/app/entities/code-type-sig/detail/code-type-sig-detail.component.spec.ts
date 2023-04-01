import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeTypeSigDetailComponent } from './code-type-sig-detail.component';

describe('CodeTypeSig Management Detail Component', () => {
  let comp: CodeTypeSigDetailComponent;
  let fixture: ComponentFixture<CodeTypeSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CodeTypeSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ codeType: { codeTypeId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CodeTypeSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CodeTypeSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load codeType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.codeType).toEqual(expect.objectContaining({ codeTypeId: 123 }));
    });
  });
});
