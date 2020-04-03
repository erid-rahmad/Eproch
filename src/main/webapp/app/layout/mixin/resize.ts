import { Component, Vue, Watch } from 'vue-property-decorator'
import { AppStoreModule as appStore, DeviceType } from '@/shared/config/store/app-store'

const WIDTH = 992 // refer to Bootstrap's responsive design

@Component({
  name: 'ResizeMixin'
})
export default class extends Vue {
  get device() {
    return appStore.device
  }

  get sidebar() {
    return appStore.sidebar
  }

  @Watch('$route')
  private onRouteChange() {
    if (this.device === DeviceType.Mobile && this.sidebar.opened) {
      appStore.CloseSideBar(false)
    }
  }

  beforeMount() {
    window.addEventListener('resize', this.resizeHandler)
  }

  mounted() {
    const isMobile = this.isMobile()
    if (isMobile) {
      appStore.ToggleDevice(DeviceType.Mobile)
      appStore.CloseSideBar(true)
    }
  }

  beforeDestroy() {
    window.removeEventListener('resize', this.resizeHandler)
  }

  private isMobile() {
    const rect = document.body.getBoundingClientRect()
    return rect.width - 1 < WIDTH
  }

  private resizeHandler() {
    if (!document.hidden) {
      const isMobile = this.isMobile()
      appStore.ToggleDevice(isMobile ? DeviceType.Mobile : DeviceType.Desktop)
      if (isMobile) {
        appStore.CloseSideBar(true)
      }
    }
  }
}
