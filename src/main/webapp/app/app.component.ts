import Vue from 'vue';
import Component from 'vue-class-component';
import ServiceWorkerUpdatePopup from '@/pwa/components/service-worker-update-popup.vue';
import LoginForm from '@/account/login-form/login-form.vue'

@Component({
  components: {
    LoginForm, ServiceWorkerUpdatePopup
  }
})
export default class App extends Vue {}
