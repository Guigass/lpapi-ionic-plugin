import { registerPlugin } from '@capacitor/core';

import type { LpapiPluginPlugin } from './definitions';

const LpapiPlugin = registerPlugin<LpapiPluginPlugin>('LpapiPlugin', {
  web: () => import('./web').then(m => new m.LpapiPluginWeb()),
});

export * from './definitions';
export { LpapiPlugin };
