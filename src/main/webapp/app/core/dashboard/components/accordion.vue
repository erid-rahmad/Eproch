<template>
    <div :id="id" class="accordion-wrapper">
        <div class="accordion-expander">
            <div class="accordion-expander__trigger" 
            :title="open ? 'Collapse' : 'Expand'"
            @click="open=!open" 
            :class="open?'accordion-active':'accordion-inactive'">
                <div style="display: flex;justify-content: space-between;width: 100%;">
                    <div>
                        <span class="accordion-title">{{ title }}</span>
                    </div>
                    <div>
                        <svg 
                            class="accordion-expander__trigger-Icon" 
                            :class="{open:open}" 
                            width="40" height="12" >
                                <polyline points="12,2 20,10 28,2" stroke-width="4" fill="none"></polyline>
                        </svg>
                    </div>
                </div>
            </div>
            <transition :name="animation">
                <div class="accordion-expander__body" v-show="open">
                    <slot></slot>
                </div>
            </transition>
        </div>
    </div>
</template>

<script>

export default {
  name: 'Accordion',
  props: {
    id: {
        type: String,
        default: 'accordion'
    },
    title: {
        type: String,
        default: 'title'
    },
    animation: {
        type: String,
        default: 'bottomToTop'
        // validator: prop => ['leftToRight', 'bounceIn', 'bottomToTop'].includes(prop)
    },
    expanded: {
        type: Boolean,
        default: false
    }
  },
  data() {
    return {
        open: this.expanded
    }
  }
}
</script>

<style lang="scss">
    .accordion-title {
        color: #FFF;
        font-size: 15px;
        font-weight: bold;
    }

    .accordion-wrapper {
        background: #4099ff;
        overflow: hidden;  
        border-radius: 5px;
        &:hover{
            box-shadow: 0 1px 18px 1px rgba(0,0,0,0.25);
        }
    }

    .accordion-expander {
        &__trigger {
            overflow: hidden;
            padding: 6px 15px;
            cursor: pointer;
            //border-bottom: 1px solid #efefef;
            /*-moz-animation-direction: ;&.active{
                //color #777
                border-bottom-color: #477dca;
            }*/
            &-Icon{
                stroke: #FFF;
                transition: transform 0.2s cubic-bezier(0.23, 1, 0.32, 1);
                &.open {
                    //stroke: tomato;
                    transform: rotate(180deg);
                }
            }
            &:hover{
                background-color: #1c3d5a;
            }
        }
        
        &__body {
            border: 1px solid #4099ff;
            border-radius: 5px;
            background: #eff0f2; //#dee2e6 //#f1f1f1
        }
    }
    
    .accordion-inactive{
        position: relative;
        &:before {
            transition: opacity .1s linear, transform .5s ease-in-out;
            position: absolute;
            border-bottom: 1px solid currentColor;
            content: '';
            width: 100%;
            left: 0;
            bottom: -1px;
        }
        &:not(:hover)::before {
            transform: scaleX(0);
            opacity: 0;
        }
    }

    @keyframes rightToLeft {
        0% {transform: translateX(100vw);}
        50% {transform: translateX(-2em);}
        100% {transform: translateX(0);}
    }
    .rightToLeft-enter-active {
        animation: rightToLeft .5s;
    }
    .rightToLeft-leave-active {
        animation: rightToLeft .5s reverse;
    }

    @keyframes leftToRight {
        0% {transform: translateX(-100vw);}
        50% {transform: translateX(2em);}
        100% {transform: translateX(0);}
    }
    .leftToRight-enter-active {
        animation: leftToRight .5s;
    }
    .leftToRight-leave-active {
        animation: leftToRight .5s reverse;
    }

    @keyframes bounceIn {
        from,
        20%,
        40%,
        60%,
        80%,
        to {
            animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
        }
        0% {
            opacity: 0;
            transform: scale3d(0.3, 0.3, 0.3);
        }
        20% {
            transform: scale3d(1.1, 1.1, 1.1);
        }
        40% {
            transform: scale3d(0.9, 0.9, 0.9);
        }
        60% {
            opacity: 1;
            transform: scale3d(1.03, 1.03, 1.03);
        }
        80% {
            transform: scale3d(0.97, 0.97, 0.97);
        }
        to {
            opacity: 1;
            transform: scale3d(1, 1, 1);
        }
    }
    
    .bounceIn-enter-active {
        animation: bounceIn .3s;
    }
    .bounceIn-leave-active{
        animation: bottomToTop .2s reverse
    }

    @keyframes bottomToTop {
        0% {background-color: #FFF; opacity: 0; transform: translateY(100%)}
        100% {transform: translateY(0);}
    }
    .bottomToTop-enter-active {
        animation: bottomToTop .5s forwards;
    }
    .bottomToTop-leave-active{
        animation: bottomToTop .5s reverse;
    }

</style>