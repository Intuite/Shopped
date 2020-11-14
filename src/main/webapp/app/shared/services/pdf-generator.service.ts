import { Injectable } from '@angular/core';
import { PdfMakeWrapper } from 'pdfmake-wrapper';
import pdfFonts from 'pdfmake/build/vfs_fonts';
// fonts provided for pdfmake

// Set the fonts to use
PdfMakeWrapper.setFonts(pdfFonts);

const pdf = new PdfMakeWrapper();

@Injectable({
  providedIn: 'root',
})
export class PdfGeneratorService {
  constructor() {}

  generatePdf(): void {
    pdf.add('Hello world!');

    pdf.create().download();
  }
}
