<template>
  <div
    class="countdown-timer"
    :class="{ 'align-right': alignRight }"
  >
    <div class="label">Remaining Time</div>
    <div class="detail">
      <div
        v-if="days"
        class="day"
      >
        <span>{{ days }}</span>
      </div>
      <div class="time">
        <span>{{ time }}</span>
      </div>
    </div>
  </div>
</template>
<script lang="ts" src="./index.component.ts"></script>
<style lang="scss" scoped>
.countdown-timer {

  &.align-right {
    text-align: right;
  }

  .detail {
    font-size: 1.4rem;

    .day,
    .time {
      display: inline-block;
    }
  }
}
</style>
