import { VueConstructor } from 'vue';

export interface IMenu {
  path: string;
  component: (() => Promise<typeof import("*.vue")>) | VueConstructor<Vue>;
  name?: string;
  redirect?: string;
  meta?: IMenuMeta;
  children?: IMenu[];
}

export interface IMenuMeta {
  title?: string;
  authorities?: string[];
  windowId?: number;
  hidden?: boolean;
  affix?: boolean;
  icon?: string;
  breadcrumb?: boolean;
  noCache?: boolean;
  activeMenu?: string;
}

export class Menu implements IMenu {
  public path: string;
  public component: (() => Promise<typeof import("*.vue")>) | VueConstructor<Vue>;
  public name?: string;
  public redirect?: string;
  public meta?: IMenuMeta;
  public children?: IMenu[] = [];

  constructor(
    path: string,
    component: (() => Promise<typeof import("*.vue")>) | VueConstructor<Vue>
  ) {
    this.path = path;
    this.component = component;
  }

  public addChild(menu: IMenu) {
    this.children.push(menu);
  }
}
