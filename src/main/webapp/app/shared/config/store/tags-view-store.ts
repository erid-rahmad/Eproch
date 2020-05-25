import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators'
import { Route } from 'vue-router'
import store from '@/shared/config/store'

export interface ITagViewState extends Partial<Route> {
  title?: string
}

export interface ITagsViewState {
  visitedViews: ITagViewState[]
  cachedViews: (string | undefined)[]
}

@Module({ dynamic: true, store, name: 'tagsViewStore', namespaced: true })
class TagsViewStore extends VuexModule implements ITagsViewState {
  public visitedViews: ITagViewState[] = []
  public cachedViews: (string | undefined)[] = []

  @Mutation
  private ADD_VISITED_VIEW(view: ITagViewState) {
    if (this.visitedViews.some(v => v.fullPath === view.fullPath)) return
    this.visitedViews.push(
      Object.assign({}, view, {
        title: view.meta.title || 'no-name'
      })
    )
  }

  @Mutation
  private ADD_CACHED_VIEW(view: ITagViewState) {
    if (this.cachedViews.includes(view.fullPath)) return
    if (!view.meta.noCache) {
      this.cachedViews.push(view.fullPath)
    }
  }

  @Mutation
  private DEL_VISITED_VIEW(view: ITagViewState) {
    console.log('DEL_VISITED_VIEW %O', view);
    for (const [i, v] of this.visitedViews.entries()) {
      if (v.fullPath === view.fullPath) {
        console.log('v.fullPath: %s', v.fullPath);
        this.visitedViews.splice(i, 1)
        break
      }
    }
  }

  @Mutation
  private DEL_CACHED_VIEW(view: ITagViewState) {
    console.log('DEL_VISITED_VIEW %O', view);
    const index = this.cachedViews.indexOf(view.name)
    index > -1 && this.cachedViews.splice(index, 1)
  }

  @Mutation
  private DEL_OTHERS_VISITED_VIEWS(view: ITagViewState) {
    this.visitedViews = this.visitedViews.filter(v => {
      return v.meta.affix || v.fullPath === view.fullPath
    })
  }

  @Mutation
  private DEL_OTHERS_CACHED_VIEWS(view: ITagViewState) {
    const index = this.cachedViews.indexOf(view.name)
    if (index > -1) {
      this.cachedViews = this.cachedViews.slice(index, index + 1)
    } else {
      // if index = -1, there is no cached tags
      this.cachedViews = []
    }
  }

  @Mutation
  private DEL_ALL_VISITED_VIEWS() {
    // keep affix tags
    const affixTags = this.visitedViews.filter(tag => tag.meta.affix)
    this.visitedViews = affixTags
  }

  @Mutation
  private DEL_ALL_CACHED_VIEWS() {
    this.cachedViews = []
  }

  @Mutation
  private UPDATE_VISITED_VIEW(view: ITagViewState) {
    for (let v of this.visitedViews) {
      if (v.fullPath === view.fullPath) {
        v = Object.assign(v, view)
        break
      }
    }
  }

  @Action
  public addView(view: ITagViewState) {
    this.ADD_VISITED_VIEW(view)
    this.ADD_CACHED_VIEW(view)
  }

  @Action
  public addVisitedView(view: ITagViewState) {
    this.ADD_VISITED_VIEW(view)
  }

  @Action
  public delView(view: ITagViewState) {
    this.DEL_VISITED_VIEW(view)
    this.DEL_CACHED_VIEW(view)
  }

  @Action
  public delCachedView(view: ITagViewState) {
    this.DEL_CACHED_VIEW(view)
  }

  @Action
  public delOthersViews(view: ITagViewState) {
    this.DEL_OTHERS_VISITED_VIEWS(view)
    this.DEL_OTHERS_CACHED_VIEWS(view)
  }

  @Action
  public delAllViews() {
    this.DEL_ALL_VISITED_VIEWS()
    this.DEL_ALL_CACHED_VIEWS()
  }

  @Action
  public delAllCachedViews() {
    this.DEL_ALL_CACHED_VIEWS()
  }

  @Action
  public updateVisitedView(view: ITagViewState) {
    this.UPDATE_VISITED_VIEW(view)
  }
}

export const TagsViewStoreModule = getModule(TagsViewStore)
