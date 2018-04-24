export class Util {

  public static isOlderThanXYears(dateString: string, years: number) {
    const date = new Date(dateString);

    const today = new Date();

    const xYearsAgo = new Date();
    xYearsAgo.setFullYear(today.getFullYear() - years);

    this.toBeginningOfDay(xYearsAgo);
    this.toBeginningOfDay(date);

    if (date.getTime() < xYearsAgo.getTime()) {
      return true;
    }

    return false;
  }

  private static toBeginningOfDay(date: Date) {
    date.setHours(0);
    date.setMinutes(0);
    date.setSeconds(0);
    date.setMilliseconds(0);
  }

  public static dateToString(date: Date) : string {
    let clonedDate = new Date(date.valueOf());
    clonedDate.setMinutes(clonedDate.getMinutes() - clonedDate.getTimezoneOffset());
    return clonedDate.toISOString().slice(0, 10);
  }

  public static dateStringToObject(date: string): Date {
    if (!date || date.length < 2) {
      return null;
    }
    let dateObject = new Date(date);
    return dateObject;
  }


  public static print(title: string, printContents: string): boolean {
      let popupWin;
      popupWin = window.open('', '_blank', 'top=0,left=0,height=100%,width=auto');
      popupWin.document.open();
      popupWin.document.write(`
        <html>
          <head>
            <title>${title}</title>
            <link href="/assets/print/print.css" rel="stylesheet">
          </head>
      <body onload="window.print();window.close()">${printContents}</body>
        </html>`
      );
      popupWin.document.close();
      return false;
  }


}
