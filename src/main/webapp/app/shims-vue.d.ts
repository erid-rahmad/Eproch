declare module '*.vue' {
  import Vue from 'vue';
  export default Vue;
}

declare module '*.gif' {
  export const gif: any;
}

declare module 'async-validator';
declare module 'splitpanes';
declare module 'v-hotkey';
declare module 'vue-form-json-schema';
declare module 'vue-easy-dnd';

type ResizeObserverBoxOptions =
  "border-box" | 
  "content-box" |
  "device-pixel-content-box";

interface ResizeObserverOptions {
  box?: ResizeObserverBoxOptions;
}

interface ResizeObservation {
  readonly lastReportedSizes: ReadonlyArray<ResizeObserverSize>;
  readonly observedBox: ResizeObserverBoxOptions;
  readonly target: Element;
}

declare var ResizeObservation: {
  prototype: ResizeObservation;
  new(target: Element): ResizeObservation;
};

interface ResizeObserver {
  disconnect(): void;
  observe(target: Element, options?: ResizeObserverOptions): void;
  unobserve(target: Element): void;
}

interface ResizeObserverEntry {
  readonly borderBoxSize: ReadonlyArray<ResizeObserverSize>;
  readonly contentBoxSize: ReadonlyArray<ResizeObserverSize>;
  readonly contentRect: DOMRectReadOnly;
  readonly devicePixelContentBoxSize: ReadonlyArray<ResizeObserverSize>;
  readonly target: Element;
}

declare var ResizeObserverEntry: {
  prototype: ResizeObserverEntry;
  new(): ResizeObserverEntry;
};

interface ResizeObserverSize {
  readonly blockSize: number;
  readonly inlineSize: number;
}

declare var ResizeObserverSize: {
  prototype: ResizeObserverSize;
  new(): ResizeObserverSize;
};

interface ResizeObserverCallback {
  (entries: ResizeObserverEntry[], observer: ResizeObserver): void;
}

export declare global { // opening up the namespace
  var ResizeObserver: { // mergin ResizeObserver to it
    prototype: ResizeObserver;
    new(callback: ResizeObserverCallback): ResizeObserver;
  }
}
