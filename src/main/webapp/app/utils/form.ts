export const buildCascaderOptions = source => {
  let parents = new Map();
  let children = new Map();
  let result = [];

  const addToChildrenCache = item => {
    let items = children.get(item.parent);
    if (!items) {
      items = [];
      children.set(item.parent, items);
    }
    items.push(item);
  };

  const addChild = (parent, item) => {
    if (!parent.children) {
      parent.children = [];
    }
    parent.children.push(item);
  };

  const addChildren = item => {
    let items = children.get(item.value);
    if (items) {
      if (!item.children) {
        item.children = [];
      }
      item.children.concat(items);
    }
  };

  source.forEach(s => {
    let item = { ...s };
    if (item.parent) {
      let parent = parents.get(item.parent);
      if (!parent) {
        addToChildrenCache(item);
      } else {
        addChild(parent, item);
      }

      addChildren(item);
    } else {
      result.push(item);
      addChildren(item);
    }
    parents.set(item.value, item);
    delete item.parent;
  });

  return result;
};
