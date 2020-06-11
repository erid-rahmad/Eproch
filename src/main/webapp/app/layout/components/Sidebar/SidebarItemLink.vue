<template>
  <a
    v-if="isExternal(to)"
    :href="to"
    target="_blank"
    rel="noopener"
  >
    <slot />
  </a>
  <div
    v-else
    @click="pushPath"
  >
    <slot />
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import { isExternal } from '@/utils/validate'

@Component({
  name: 'SidebarItemLink'
})
export default class extends Vue {
  @Prop({ required: true }) private to!: string

  private isExternal = isExternal

  private pushPath() {
    this.$router.push({
      path: this.to,
      query: {
        t: '' + Date.now()
      }
    })
  }
}
</script>
