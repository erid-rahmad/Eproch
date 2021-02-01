import { Observable, Observer } from 'rxjs';
import SockJS from 'sockjs-client';
import * as Stomp from 'webstomp-client';
import DashBoard from './dashboard.vue';

export default class DashboardService {
  public stompClient: Stomp.Client = null;
  public subscriber: any = null;
  public connection: Promise<any>;
  public connectedPromise: any = null;
  public listener: Observable<any>;
  public listenerObserver: Observer<any> = null;
  public alreadyConnectedOnce = false;

  private dashboard: DashBoard;

  constructor(dashboard: DashBoard) {
    this.dashboard = dashboard;
    this.connection = this.createConnection();
    this.listener = this.createListener();
  }

  public connect(): void {
    if (this.connectedPromise === null) {
      this.connection = this.createConnection();
    }
    // building absolute path so that websocket doesn't fail when deploying with a context path
    const loc = window.location;
    let url = `//${loc.host}${loc.pathname}websocket/dashboard`;
    const authToken = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
    if (authToken) {
      url += '?access_token=' + authToken;
    }
    const socket = new SockJS(url);
    this.stompClient = Stomp.over(socket);
    const headers = {};
    this.stompClient.connect(headers, () => this.afterConnect());
  }

  public afterConnect() {
    this.connectedPromise('success');
    this.connectedPromise = null;
    this.refresh();
    if (!this.alreadyConnectedOnce) {
      this.alreadyConnectedOnce = true;
    }
  }

  public disconnect(): void {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
      this.stompClient = null;
    }
    this.alreadyConnectedOnce = false;
  }

  public receive(): Observable<any> {
    return this.listener;
  }

  public refresh(): void {
    (<any>this.dashboard).refresh();
  }

  public subscribe(): void {
    this.connection.then(() => {
      this.subscriber = this.stompClient.subscribe('/topic/dashboard', data => {
        this.listenerObserver.next(data.body);
      });
    });
  }

  public unsubscribe(): any {
    if (this.subscriber !== null) {
      this.subscriber.unsubscribe();
    }
  }

  public createListener(): any {
    return new Observable(observer => {
      this.listenerObserver = observer;
    });
  }

  public createConnection(): Promise<any> {
    return new Promise((resolve, reject) => (this.connectedPromise = resolve));
  }
}
