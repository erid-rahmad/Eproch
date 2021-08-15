import dayjs from 'dayjs';
import duration from "dayjs/plugin/duration";
import isSameOrAfter from "dayjs/plugin/isSameOrAfter";
import { Component, Vue, Watch } from "vue-property-decorator";

dayjs.extend(duration);
dayjs.extend(isSameOrAfter);

const CountdownTimerProps = Vue.extend({
  props: {
    alignRight: Boolean,
    autoStart: Boolean,
    duration: String,
    paused: Boolean,
    toDate: String
  }
});

@Component
export default class CountdownTimer extends CountdownTimerProps {

  private currentDate = null;
  private endDate = null;
  private intervalId = null;
  private running: boolean = false;
  private timerDuration = null;

  get days() {
    return this.formatDays(this.timerDuration?.days());
  }

  get hours() {
    return this.formatTime(this.timerDuration?.hours());
  }

  get minutes() {
    return this.formatTime(this.timerDuration?.minutes());
  }

  get seconds() {
    return this.formatTime(this.timerDuration?.seconds());
  }

  get time() {
    return `${this.hours}:${this.minutes}:${this.seconds}`;
  }

  @Watch('duration')
  onDurationChanged(value: string) {
    console.log('duration changed', value);
    if (value) {
      this.timerDuration = dayjs.duration(value);
    }
  }

  @Watch('toDate')
  onToDateChanged(toDate: string) {
    console.log('toDate changed', toDate);
    if (!this.duration && toDate) {
      this.endDate = dayjs(this.toDate);
    }

    if (this.autoStart && !this.running) {
      this.start();
    }
  }

  created() {
    if (this.duration) {
      this.timerDuration = dayjs.duration(this.duration);
    }

    this.onToDateChanged(this.toDate);
  }

  beforeDestroy() {
    this.stop();
  }

  start() {
    if (this.running) {
      return;
    }

    this.intervalId = setInterval(() => {
      this.updateCurrentDate();
    }, 1000);
  }

  stop() {
    if (!this.running) {
      return;
    }

    clearInterval(this.intervalId);
    this.running = false;
  }

  private updateCurrentDate() {
    this.currentDate = dayjs();

    if (!this.running && this.timerDuration) {
      this.endDate = this.currentDate.add(this.timerDuration);
    } else {
      this.timerDuration = dayjs.duration(this.endDate.diff(this.currentDate));
    }

    this.$emit('update:duration', this.timerDuration.toISOString());
    this.running = true;

    if (this.currentDate.isSameOrAfter(this.endDate)) {
      this.stop();
    }
  }

  private formatDays(value: number) {
    if (!value) {
      return '';
    }

    return `${value} Day${value > 1 ? 's' : ''}`;
  }

  private formatTime(value: number) {
    if (!value) {
      return '00';
    }

    const normalizedValue = '' + Math.floor(value);
    return normalizedValue.length > 1 ? normalizedValue : `0${normalizedValue}`;
  }
}