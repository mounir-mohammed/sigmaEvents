import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CacheService {
  private cacheKey = 'appCache';

  constructor() {}

  set(key: string, value: any): void {
    const cache = this.getCache();
    cache[key] = value;
    this.saveCache(cache);
  }

  get(key: string): any {
    const cache = this.getCache();
    return cache[key];
  }

  delete(key: string): void {
    const cache = this.getCache();
    delete cache[key];
    this.saveCache(cache);
  }

  reset(): boolean {
    try {
      localStorage.removeItem(this.cacheKey);
      console.log('Cache reset successful');
      return true;
    } catch (error) {
      console.error('Cache reset failed:', error);
      return false;
    }
  }

  private getCache(): any {
    const encodedCacheStr = localStorage.getItem(this.cacheKey);
    if (encodedCacheStr) {
      const decodedCacheStr = atob(encodedCacheStr);
      return JSON.parse(decodedCacheStr);
    }
    return {};
  }

  private saveCache(cache: any): void {
    const encodedCacheStr = btoa(JSON.stringify(cache));
    localStorage.setItem(this.cacheKey, encodedCacheStr);
  }
}
