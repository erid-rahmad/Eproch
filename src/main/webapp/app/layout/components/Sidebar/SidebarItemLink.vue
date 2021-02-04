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
import { RawLocation } from 'vue-router';

@Component({
  name: 'SidebarItemLink'
})
export default class extends Vue {
  @Prop({ required: true }) private to!: string

  isExternal = isExternal

  pushPath() {
    const location: RawLocation = {
      path: this.to,
    };

    if (this.to !== '/') {
      location.query = {
        t: '' + Date.now(),
      };
    }

    this.$router.push(location);
  }
}
</script>
