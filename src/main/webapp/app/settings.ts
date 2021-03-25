interface ISettings {
  title: string; // Overrides the default title
  showSettings: boolean; // Controls settings panel display
  showTagsView: boolean; // Controls tagsview display
  showSidebarLogo: boolean; // Controls siderbar logo display
  fixedHeader: boolean; // If true, will fix the header component
  dateDisplayFormat: string;
  dateTimeDisplayFormat: string;
  dateValueFormat: string;
  dateTimeValueFormat: string;
  errorLog: string[]; // The env to enable the errorlog component, default 'production' only
  sidebarTextTheme: boolean; // If true, will change active text color for sidebar based on theme
  devServerPort: number; // Port number for webpack-dev-server
  mockServerPort: number; // Port number for mock server

  // e-Verification module.
  taxNoPattern13digits: {
    blocks: number[],
    delimiters: string[],
    numericOnly: boolean
  };

  taxNoPattern16digits: {
    blocks: number[],
    delimiters: string[],
    numericOnly: boolean
  };
}

// You can customize below settings :)
const settings: ISettings = {
  title: 'e-Procurement',
  showSettings: false,
  showTagsView: true,
  fixedHeader: true,
  showSidebarLogo: true,
  dateDisplayFormat: 'dd/MM/yyyy',
  dateTimeDisplayFormat: 'dd/MM/yyyy HH:mm',
  dateValueFormat: 'yyyy-MM-dd',
  dateTimeValueFormat: 'yyyy-MM-dd HH:mm',
  errorLog: ['production'],
  sidebarTextTheme: true,
  devServerPort: 9527,
  mockServerPort: 9528,

  taxNoPattern13digits: {
    blocks: [3, 2, 8],
    delimiters: ['-', '.'],
    numericOnly: true
  },

  taxNoPattern16digits: {
    blocks: [3, 3, 2, 8],
    delimiters: ['.', '-', '.'],
    numericOnly: true
  }
}

export default settings
