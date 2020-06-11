import Vue from 'vue';
import axios, { AxiosPromise } from 'axios';
import { DialogEventBus } from '@/core/event/dialog-event-bus';

export default class LoginService {
  public openLogin(instance: Vue): void {
    DialogEventBus.$emit('show-login')
  }
}
