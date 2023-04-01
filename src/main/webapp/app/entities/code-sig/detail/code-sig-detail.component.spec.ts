import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeSigDetailComponent } from './code-sig-detail.component';

describe('CodeSig Management Detail Component', () => {
  let comp: CodeSigDetailComponent;
  let fixture: ComponentFixture<CodeSigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CodeSigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ code: { codeId: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CodeSigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CodeSigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load code on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.code).toEqual(expect.objectContaining({ codeId: 123 }));
    });
  });
});
