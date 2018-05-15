import { DialysClientPage } from './app.po';

describe('dialys-client App', () => {
  let page: DialysClientPage;

  beforeEach(() => {
    page = new DialysClientPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
